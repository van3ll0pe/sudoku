package org.sudoku;

public class MoveStack {
    private class Node {
        private Move move;
        private Node next;

        public Node(Move move, Node next) {
            this.move = move;
            this.next = next;
        }
    }

    private Node stack;

    public MoveStack() {
        this.stack = null;
    }

    public boolean isEmpty() {
        return this.stack == null;
    }

    public void add(Move move) {
        stack = new Node(move, stack);
    }

    public boolean pop() {
        if (isEmpty())
            return false;

        stack = stack.next;
        return true;
    }

    public Move get() {
        return stack.move;
    }

    public void clear() {
        this.stack = null;
    }
}
