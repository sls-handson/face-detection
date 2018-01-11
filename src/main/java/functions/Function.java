package functions;

import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.ImageTooLargeException;
import com.amazonaws.services.rekognition.model.InvalidImageFormatException;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;

import exception.FaceAlreadyExistsError;
import exception.PhotoDoesNotMeetRequirementError;
import functions.Function.Input;
import functions.Function.Output;

public class Function implements java.util.function.Function<Input, Output> {


	private final AmazonRekognition amazonRekognition;

	public Function(AmazonRekognition amazonRekognition) {
		this.amazonRekognition = amazonRekognition;
	}

	@Override
	public Output apply(final Input input) {
		final Output output = new Output();

		final Image image = new Image().withS3Object(new S3Object().withBucket(input.getS3Bucket()).withName(input.getS3Key()));

		try {
			final DetectFacesRequest detectFacesRequest =
					new DetectFacesRequest()
					.withImage(image)
					.withAttributes("ALL");

			final DetectFacesResult detectFacesResult = this.amazonRekognition.detectFaces(detectFacesRequest);

			final List<FaceDetail> faceDetails = detectFacesResult.getFaceDetails();

			if (faceDetails.size() != 1) {
				throw new PhotoDoesNotMeetRequirementError("Detected " + faceDetails.size() + " faces in the photo.");
			}

			final FaceDetail detectedFaceDetails = faceDetails.get(0);

			if (detectedFaceDetails.getSunglasses().isValue()){
				throw new PhotoDoesNotMeetRequirementError("Face is wearing sunglasses");
			}

			output.setUserId(input.getUserId());
			output.setS3Bucket(input.getS3Bucket());
			output.setS3Key(input.getS3Key());
			output.setDetectedFaceDetails(faceDetails.get(0));

		} catch (ImageTooLargeException e) {
			throw new PhotoDoesNotMeetRequirementError(e.getMessage());
		} catch (InvalidImageFormatException e) {
			throw new PhotoDoesNotMeetRequirementError("Unsupported image file format. Only JPEG or PNG is supported");
		} catch (Exception e) {
			throw new PhotoDoesNotMeetRequirementError(e.getMessage());
		}

		try {
			final SearchFacesByImageRequest searchFacesByImageRequest =
					new SearchFacesByImageRequest()
					.withCollectionId(input.getCollectionId())
					.withImage(image)
					.withFaceMatchThreshold(70F)
					.withMaxFaces(2);

			final SearchFacesByImageResult searchFacesByImageResult = this.amazonRekognition.searchFacesByImage(searchFacesByImageRequest);

			final List<FaceMatch> faceImageMatches = searchFacesByImageResult.getFaceMatches();

			if (faceImageMatches.size() > 0) {
				throw new FaceAlreadyExistsError("Face in the picture is already in the system");
			}

		} catch (Exception e) {
			throw new FaceAlreadyExistsError(e.getMessage());
		}

		return output;
	}

	public static final class Input {
		private String userId, s3Key, s3Bucket, collectionId;

		public String getS3Key() {
			return s3Key;
		}

		public void setS3Key(String s3Key) {
			this.s3Key = s3Key;
		}

		public String getS3Bucket() {
			return s3Bucket;
		}

		public void setS3Bucket(String s3Bucket) {
			this.s3Bucket = s3Bucket;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getCollectionId() {
			return collectionId;
		}

		public void setCollectionId(String collectionId) {
			this.collectionId = collectionId;
		}

	}

	public static final class Output {
		private String userId, s3Key, s3Bucket, collectionId;
		private FaceDetail detectedFaceDetails;

		public String getS3Key() {
			return s3Key;
		}

		public void setS3Key(String s3Key) {
			this.s3Key = s3Key;
		}

		public String getS3Bucket() {
			return s3Bucket;
		}

		public void setS3Bucket(String s3Bucket) {
			this.s3Bucket = s3Bucket;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getCollectionId() {
			return collectionId;
		}

		public void setCollectionId(String collectionId) {
			this.collectionId = collectionId;
		}

		public FaceDetail getDetectedFaceDetails() {
			return detectedFaceDetails;
		}

		public void setDetectedFaceDetails(FaceDetail detectedFaceDetails) {
			this.detectedFaceDetails = detectedFaceDetails;
		}
	}

}
