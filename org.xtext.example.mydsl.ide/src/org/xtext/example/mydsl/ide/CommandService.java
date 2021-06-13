package org.xtext.example.mydsl.ide;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import org.xtext.example.mydsl.ide.server.progress.ProgressExecutor;

public class CommandService implements IExecutableCommandService {

	@Inject
	private ProgressExecutor progressExecutor;

	@Override
	public List<String> initialize() {
		return Lists.newArrayList("mydsl.a", "mydsl.b", "mydsl.c");
	}

	@Override
	public Object execute(ExecuteCommandParams params, ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		if ("mydsl.a".equals(params.getCommand())) {
			String uri = ((com.google.gson.JsonPrimitive)Iterables.getFirst(params.getArguments(), null)).getAsString();
			if (uri != null) {
				try {
					return access.doRead(uri, (ILanguageServerAccess.Context it) -> commandA(params, access)).get();
				} catch (InterruptedException | ExecutionException e) {
					return e.getMessage();
				}
			} else {
				return "Param Uri Missing";
			}
		} else if ("mydsl.b".equals(params.getCommand())) {
			return "Command B";
		}
		return "Bad Command";
	}

	private String commandA(ExecuteCommandParams params, ILanguageServerAccess access) {
		progressExecutor.execute(params, access);
		return "Command A";
	}


}
