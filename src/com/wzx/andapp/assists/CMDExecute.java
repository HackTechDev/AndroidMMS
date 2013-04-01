package com.wzx.andapp.assists;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CMDExecute {

	public synchronized String run(String[] cmd, String workdirectory)
			throws IOException {
		String result = "";

		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			// set working directory
			if (workdirectory != null)
				builder.directory(new File(workdirectory));
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream in = process.getInputStream();

			final BufferedReader errReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));
			final StringBuffer errMsg = new StringBuffer();
			
			Thread errThread = new Thread() {
				@Override
				public void run() {
					try {
						String line = errReader.readLine();
						while ((line != null) && !isInterrupted()) {
							errMsg.append(line);
							errMsg.append(System.getProperty("line.separator"));
							line = errReader.readLine();
						}
					} catch (IOException ioe) {
						// LOG.warn("Error reading the error stream", ioe);
					}
				}
			};
			try {
				errThread.start();
			} catch (IllegalStateException ise) {
			}
			
			byte[] re = new byte[1024];
			while (in.read(re) != -1) {
				System.out.println(new String(re));
				result = result + new String(re);
			}
			in.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			result = cmdexe.run(args, "D:\\MyProject\\colimas\\axis_c");
			System.out.println(result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
