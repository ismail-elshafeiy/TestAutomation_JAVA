package engine;

import engine.listeners.Logger;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;

public class FileActions {
	public static FileActions getInstance () {
		return new FileActions();
	}

	/**
	 * Copies a file from sourceFilePath to destinationFilePath on the local storage
	 *
	 * @param sourceFilePath      the full (absolute) path of the source file that
	 *                            will be copied
	 * @param destinationFilePath the full (absolute) path of the desired location
	 *                            and file name for the newly created copied file
	 */
	public void copyFile (String sourceFilePath, String destinationFilePath) {
		File sourceFile = new File(sourceFilePath);
		File destinationFile = new File(destinationFilePath);
		copyFile(sourceFile, destinationFile);
		Logger.logStep("Source File: \"" + sourceFilePath + "\" | Destination File: \"" + destinationFilePath + "\"");
	}

	public void copyFolder (String sourceFolderPath, String destinationFolderPath) {
		File sourceFolder = new File(sourceFolderPath);
		File destinationFolder = new File(destinationFolderPath);
		try {
			FileUtils.copyDirectory(sourceFolder, destinationFolder);
			Logger.logStep(
					"Source Folder: \"" + sourceFolderPath + "\" | Destination Folder: \"" + destinationFolder + "\"");
		} catch ( IOException rootCauseException ) {
			Assert.fail(rootCauseException.getMessage());
		}
	}

	private void copyFile (File sourceFile, File destinationFile) {
		try {
			FileUtils.copyFile(sourceFile, destinationFile);
		} catch ( IOException rootCauseException ) {

			Assert.fail(rootCauseException.getMessage());
		}
	}

}
