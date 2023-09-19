package ru.ptrff;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.model.Factory.graph;

public class TreeBuilder {

    Set<Pair<String, String>> nodes = new HashSet<>();
    private Graph graph;

    public TreeBuilder() {
        this.graph = graph().directed().graphAttr().with(Rank.dir(TOP_TO_BOTTOM));
    }

    public void addNode(String parentLabel, String newLabel) {
        Pair<String, String> pair = new Pair<>(parentLabel, newLabel);
        nodes.add(pair);

        Node parentNode = Factory.node(parentLabel).with(Label.of(parentLabel));
        Node newNode = Factory.node(newLabel).with(Label.of(newLabel));

        graph = graph.with(parentNode.link(newNode));
    }


    public void saveTree(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(path.toString()));
            System.out.println("Tree saved to: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error: Unable to save the tree");
            e.printStackTrace();
        }
    }

    public void saveTreeAsGv(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Graphviz.fromGraph(graph).render(Format.DOT).toFile(new File(path.toString()));
            System.out.println("Tree saved to: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error: Unable to save the tree");
            e.printStackTrace();
        }
    }

    public boolean isHere(String parent, String child) {
        Pair<String, String> pair = new Pair<>(parent, child);
        return nodes.contains(pair);
    }

}
