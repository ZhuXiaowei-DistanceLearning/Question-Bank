package com.zxw.designpattern.visual;

class DrawVisitor implements Visitor {

    @Override
    public void visit(Node node) {
        System.out.println("draw node");
    }

    @Override
    public void visit(Factory factory) {
        System.out.println("draw factory");
    }

    @Override
    public void visit(Building building) {
        System.out.println("draw building");
    }

    @Override
    public void visit(School school) {
        System.out.println("draw school");
    }
}