package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class RunFileGenerator implements Plugin<Project> {
	
	private final static String WIN = "\r\n";
	
	private final static String LINUX = "\n";

	@Override
	public void apply(Project project) {
		RunFileGeneratorExtension data = project.getExtensions()
				.create("script", RunFileGeneratorExtension.class);
		
		project.task("generate-run-script").doLast(task -> {
			try {
				generateLinuxScript(data.getScriptName() + ".sh", data.getAppName(), data.getMainClass());
    			generateWindowsScript(data.getScriptName() + ".bat", data.getAppName(), data.getMainClass());
    			generateRunScript(data.getRunFileName(), data.getAppName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
	}
	
	private void generateLinuxScript(String fileName, String appName, String mainClass) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			return;
		}
		try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
			br.write(
				"#!/bin/bash" + LINUX
				+ String.format("java -cp \"%s.jar;lib/*\" %s", appName, mainClass) + LINUX
			);
		}
	}
	
	private void generateWindowsScript(String fileName, String appName, String mainClass) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			return;
		}
		try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
			br.write(
				String.format("start \"\" javaw -cp \"%s.jar;lib\\*\" %s", appName, mainClass)
			);
		}
	}
	
	private void generateRunScript(String fileName, String appName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			return;
		}
		try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
			br.write(
				"#!/bin/bash" + WIN + LINUX
				+ "# commented if run on linux" + WIN
				+ "GOTO Windows" + WIN + LINUX
				
				+ "# Linux" + LINUX
				+ "./bin/" + appName + ".sh" + LINUX
				+ "exit" + LINUX + WIN
				
				+ ":Windows" + WIN
				+ "cd %~dp0" + WIN
				+ "./bin/" + appName + ".bat"+ WIN
				+ "exit" + WIN + LINUX
			);
		}
	}

}
