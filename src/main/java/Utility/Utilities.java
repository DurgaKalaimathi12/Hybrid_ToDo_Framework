package Utility;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilities {
	public static String getSystemPath() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}
}
