package de.tf.utils;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 
 * @author CoasterFreak
 *
 */
public class FileConfig extends YamlConfiguration {
	
	public String FileConfigFileName;

	public String FileSeperator;

	public void log(String Message) {

		System.out.print("[FileConfig] " + Message);
	}

	public FileConfig(String Package, String FileName) {

		FileSeperator = System.getProperty("file.separator");

		FileConfigFileName = "plugins" + FileSeperator + Package + FileSeperator + FileName;

		try {
			load(FileConfigFileName);

		} catch (Exception e) {
			log("Error Loading file " + FileConfigFileName + "  " + e.getMessage().toString());
		}
	}

	public void SaveConfig() {

		try {
			save(FileConfigFileName);

		} catch (Exception e) {
			log("Error Saving file " + FileConfigFileName + "  " + e.getMessage().toString());
		}
	}
}
