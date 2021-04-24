package org.xtext.example.mydsl.ide.server.folding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.lsp4j.FoldingRange;
import org.eclipse.lsp4j.FoldingRangeRequestParams;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.xtext.example.mydsl.myDsl.Model;
import org.xtext.example.mydsl.myDsl.Greeting;
import org.xtext.example.mydsl.myDsl.GreetingBlock;

public class FoldingService {
	public List<FoldingRange> folding(Document document, XtextResource resource, FoldingRangeRequestParams params,
			CancelIndicator cancelIndicator) {
		List<FoldingRange> list = createFoldingRangeList(resource);
		return list;
	}
	
	private List<FoldingRange> createFoldingRangeList(XtextResource resource) {
		List<FoldingRange> list = new ArrayList<FoldingRange>();
		EObject o = resource.getContents().get(0);
		if (o instanceof Model) {
			Model m = (Model)o;
			GreetingBlock gb = m.getGreetingBlock();
			list.addAll(creatteFoldingRangeList(gb));
		}
		return list;
	}
	
	private List<FoldingRange> creatteFoldingRangeList(GreetingBlock block) {
		List<FoldingRange> list = new ArrayList<FoldingRange>();
		INode n = NodeModelUtils.getNode(block);
		FoldingRange fr = new FoldingRange();
		fr.setStartLine(n.getStartLine() - 1);
		fr.setEndLine(n.getEndLine() - 1);
		list.add(fr);
		return list;
	}

}
