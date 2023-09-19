package ru.ptrff;

public class Main {

    private static final String root = "pandas";

    private static void buildDependencyTree(TreeBuilder treeBuilder, String parent, int depth, int maxDepth) {
        if (depth > maxDepth) {
            return;
        }

        for (String dependency : DependencyFetcher.getDependencies(parent)) {
            if (!treeBuilder.isHere(parent, dependency)) {
                treeBuilder.addNode(parent, dependency);
                System.out.println(parent + "   " + dependency);
                buildDependencyTree(treeBuilder, dependency, depth + 1, maxDepth);
            }
        }
    }

    private static void buildDependencyTreeInfinite(TreeBuilder treeBuilder, String parent) {
        for (String dependency : DependencyFetcher.getDependencies(parent)) {
            if(!treeBuilder.isHere(parent, dependency)) {
                treeBuilder.addNode(parent, dependency);
                System.out.println(parent+"   "+dependency);
                buildDependencyTreeInfinite(treeBuilder, dependency);
            }
        }
    }
    public static void main(String[] args){

        TreeBuilder treeBuilder = new TreeBuilder();

        buildDependencyTree(treeBuilder, root, 0, 1);

        treeBuilder.saveTreeAsGv(root +".gv");
        treeBuilder.saveTree(root+".png");
    }
}