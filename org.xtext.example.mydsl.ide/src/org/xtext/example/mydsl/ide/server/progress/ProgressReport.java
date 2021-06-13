package org.xtext.example.mydsl.ide.server.progress;

import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.WorkDoneProgressCreateParams;
import org.eclipse.lsp4j.WorkDoneProgressBegin;
import org.eclipse.lsp4j.WorkDoneProgressEnd;
import org.eclipse.lsp4j.WorkDoneProgressReport;
import org.eclipse.lsp4j.ProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

public class ProgressReport {
    private LanguageClient client = null;
    private Either<String, Number> token;

    public ProgressReport(LanguageClient client, String id) {
        this.client = client;
        this.token = Either.forLeft(id);
    }

    public void create() {
        if (client != null) {
            client.createProgress(new WorkDoneProgressCreateParams(token));
        }
    }

    public void begin(String title, String message) {
        if (client != null) {
            client.createProgress(new WorkDoneProgressCreateParams(token));

            WorkDoneProgressBegin wdpb = new WorkDoneProgressBegin();
            wdpb.setTitle(title);
            wdpb.setMessage(message);
            wdpb.setPercentage(Integer.valueOf(0));
            client.notifyProgress(new ProgressParams(token, wdpb));
        }
    }

    public void report(String message, int progress) {
        if (client != null) {
            WorkDoneProgressReport wdpr = new WorkDoneProgressReport();
            wdpr.setMessage(message);
            wdpr.setPercentage(Integer.valueOf(progress));
            client.notifyProgress(new ProgressParams(token, wdpr));
        }
    }

    public void end() {
		WorkDoneProgressEnd wdpe = new WorkDoneProgressEnd();
		client.notifyProgress(new ProgressParams(token, wdpe));
        client = null;
    }

}