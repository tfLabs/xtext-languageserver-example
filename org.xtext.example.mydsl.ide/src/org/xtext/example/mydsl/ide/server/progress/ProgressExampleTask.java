package org.xtext.example.mydsl.ide.server.progress;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.common.collect.Iterables;
import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

public class ProgressExampleTask implements Runnable {
	private ExecuteCommandParams params;
	private String recieved;
	private String started;
	private DateTimeFormatter pattern;
	private int progress = 0;
	private ProgressReport reporter;
	private int busy = 3000;
	private int step = 100;
	private int stepNum = 5;
	private int end = 2000;

	public ProgressExampleTask(ExecuteCommandParams params, ProgressReport reporter) {
		this.params = params;
		this.reporter = reporter;
		this.pattern = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.recieved = LocalDateTime.now().format(pattern);
	}

    public void run() {
		started = LocalDateTime.now().format(pattern);
		String u = ((com.google.gson.JsonPrimitive)Iterables.getFirst(params.getArguments(), null)).getAsString();
		URI uri = URI.createURI(u);
		reporter.begin("recieved: " + recieved + " " + "started: " + started + " " + "processing: " + uri.lastSegment(), "prepare...");
		progress = 0;
		try {
			Thread.sleep(busy);
		} catch (InterruptedException e){
		}

		for (; progress < 100; progress += stepNum) {
			reporter.report("processed " + progress + "%", progress);
			try {
				Thread.sleep(step);
			} catch (InterruptedException e){
			}
		}
		reporter.report("done", progress);
		try {
			Thread.sleep(end);
		} catch (InterruptedException e){
		}

		reporter.end();
    }
}
