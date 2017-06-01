/**
 * Windows Spotlight Image Retriever
 * Zac Hayes
 *
 * Copies all of the files from the folder where the images for Windows Spotlight (lock screen background setting) are stored,
 *	renames them with a .jpg extension,
 *	and opens a file viewer so the user can copy which images they want to keep.
 *
 * Steps derived from the how-to on How-To-Geek:
 * http://www.howtogeek.com/247643/how-to-save-windows-10s-lock-screen-spotlight-images-to-your-hard-drive/
 */
package wsir;

import java.io.File;
import java.io.*;
import java.nio.channels.*;

public class WSIR
{
	public static void main(String[] args)
	{
		// get the assets folder where the images are stored
		File assets = new File(String.format("%s/AppData/Local/Packages/Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy/LocalState/Assets", System.getProperty("user.home")));
		// clear the target folder ([project folder]/Possible Images)
		File imageFolder = new File(String.format("%s/Possible Images", System.getProperty("user.dir")));
		if (!imageFolder.exists() && !imageFolder.isDirectory())
			imageFolder.mkdir();
		for (File file : imageFolder.listFiles())
		{
			file.delete();
		}
		// copy everything into it and add ".jpg" extension
		for (File file : assets.listFiles())
		{
			try
			{
				copyFile(file, new File(String.format("%s\\%s.jpg", imageFolder.getCanonicalPath(), file.getName())));
			} catch (IOException ex)
			{
				System.err.println("Error - " + ex.getMessage());
			}
		}
		try
		{
			// open Windows Explorer to Possible Images folder
			Runtime.getRuntime().exec(String.format("explorer.exe %s", imageFolder.getCanonicalPath()));
		} catch (IOException ex)
		{
			System.err.println("Error - " + ex.getMessage());
		}
	}

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}
	}
}