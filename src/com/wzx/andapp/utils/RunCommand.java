package com.wzx.andapp.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RunCommand {
	static ExecutorService es = Executors.newCachedThreadPool();

	private static final int BUFSIZE = 8 * 1024;

	private static synchronized String execute(String[] cmd, String exectdir)
			throws IOException {
		String result = null;
		InputStream in = null;
		ByteArrayOutputStream bao = null;
		try {
			// Runtime.getRuntime().exec("su");
			// /system/bin/sh,-c
			ProcessBuilder builder = new ProcessBuilder(cmd);
			if (exectdir != null)
				builder.directory(new File(exectdir));
			builder.redirectErrorStream(true);
			Process process = builder.start();
			in = process.getInputStream();

			final BufferedReader errReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()), BUFSIZE);
			final StringBuffer errMsg = new StringBuffer();
			Thread errThread = new Thread() {
				public void run() {
					try {
						String line = null;
						while ((line = errReader.readLine()) != null
								&& !isInterrupted()) {
							errMsg.append(line).append(
									System.getProperty("line.separator"));
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} finally {
						try {
							if (errReader != null) {
								errReader.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};
			try {
				errThread.start();
			} catch (IllegalStateException ise) {
			}

			byte[] re = new byte[BUFSIZE];
			int a = 0;
			bao = new ByteArrayOutputStream(BUFSIZE);
			while ((a = in.read(re)) != -1) {
				bao.write(re, 0, a);
			}
			result = new String(bao.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bao != null) {
					bao.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static synchronized String execute(String cmd, String execdir)
			throws IOException {
		// /system/bin/sh
		return execute(new String[] { "sh", "-c", cmd }, execdir);
	}

	public static String postTimeout(final String urlAddr, final Map map,
			long timeOut, final String cmd, final String execdir) {
		long start = System.currentTimeMillis();

		InputStream in = null;
		ByteArrayOutputStream bao = null;
		final ProcessBuilder builder = new ProcessBuilder(cmd);
		Future<String> future1 = es.submit(new Callable<String>() {
			public String call() throws Exception {
				String result = null;
				InputStream in = null;
				ByteArrayOutputStream bao = null;
				try {
					// Runtime.getRuntime().exec("su");
					// /system/bin/sh,-c
					if (execdir != null)
						builder.directory(new File(execdir));
					builder.redirectErrorStream(true);
					Process process = builder.start();
					in = process.getInputStream();

					final BufferedReader errReader = new BufferedReader(
							new InputStreamReader(process.getErrorStream()),
							BUFSIZE);
					final StringBuffer errMsg = new StringBuffer();
					Thread errThread = new Thread() {
						public void run() {
							try {
								String line = null;
								while ((line = errReader.readLine()) != null
										&& !isInterrupted()) {
									errMsg.append(line)
											.append(System
													.getProperty("line.separator"));
								}
							} catch (IOException ioe) {
								ioe.printStackTrace();
							} finally {
								try {
									if (errReader != null) {
										errReader.close();
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					};
					try {
						errThread.start();
					} catch (IllegalStateException ise) {
					}

					byte[] re = new byte[BUFSIZE];
					int a = 0;
					bao = new ByteArrayOutputStream(BUFSIZE);
					while ((a = in.read(re)) != -1) {
						bao.write(re, 0, a);
					}
					result = new String(bao.toByteArray());
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (in != null) {
							in.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if (bao != null) {
							bao.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return result;
			}

		});

		String s = null;
		try {
			s = future1.get(timeOut, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			future1.cancel(true);
			e.printStackTrace();
		}
		// System.out.println(System.currentTimeMillis() - start);
		// System.out.println(s);
		return s;
	}

	public static void main(String[] args) {
		String result = null;
		RunCommand cmdexe = new RunCommand();
		try {
			result = cmdexe.execute(args, "D:\\MyProject\\colimas\\axis_c");
			System.out.println(result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
