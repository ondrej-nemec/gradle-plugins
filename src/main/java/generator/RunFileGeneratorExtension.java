package generator;

public class RunFileGeneratorExtension {
	
	private String dir;
	
	private String appName;
	
	private String runFileName = "run.bat";
	
	private String mainClass;
	
	public String getScriptName() {
		return dir + "/" + appName;
	}

	public String getDir() {
		return dir;
	}

	public String getAppName() {
		return appName;
	}

	public String getRunFileName() {
		return runFileName;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setRunFileName(String runFileName) {
		this.runFileName = runFileName;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

}
