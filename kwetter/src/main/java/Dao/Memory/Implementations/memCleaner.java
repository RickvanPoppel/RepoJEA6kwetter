package Dao.Memory.Implementations;

public class memCleaner {

    memCollectionObject im = memCollectionObject.getInstance();

    public void clearMemory(){
        im.clearMemory();
    }
}
