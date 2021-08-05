package designpattern.visual;

interface Visitor {
    void visit(Node node);
    void visit(Factory factory);
    void visit(Building building);
    void visit(School school);
}