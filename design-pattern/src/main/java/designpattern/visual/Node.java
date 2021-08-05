package designpattern.visual;

interface Node {
    String getName();
    String getDescription();
    void accpet(Visitor v);
}