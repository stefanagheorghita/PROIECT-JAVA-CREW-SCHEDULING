package example.model.entity;

public class NodeImpl implements Node {

    private boolean source;

    public NodeImpl(boolean source){
        this.source = source;
    }

    public boolean isSource(){
        return source;
    }

    public boolean isSink(){
        return !source;
    }

}
