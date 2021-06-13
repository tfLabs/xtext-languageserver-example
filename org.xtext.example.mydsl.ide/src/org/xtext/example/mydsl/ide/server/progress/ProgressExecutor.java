package org.xtext.example.mydsl.ide.server.progress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Singleton;

import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;

import java.util.Date;
import java.util.Random;

public class ProgressExecutor {
	private final ExecutorService queue = Executors.newFixedThreadPool(3, 
			new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ProgressExecutor-Queue-%d").build());

	public void execute(ExecuteCommandParams params, ILanguageServerAccess access) {
		LanguageClient client = access.getLanguageClient();
		ProgressExampleTask task = new ProgressExampleTask(params, new ProgressReport(client, generatingRandomAlphabeticString()));
		queue.submit(task);
    }

	private String generatingRandomAlphabeticString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
	
		String generatedString = random.ints(leftLimit, rightLimit + 1)
			.limit(targetStringLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		
		return generatedString;
	}

}