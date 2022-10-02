public class SearchTree implements NodeList {
    ListItem root;

    public SearchTree(ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return root;
    }

    @Override
    public boolean addItem(ListItem item) {
        ListItem currItem = this.root;
        if(currItem == null) {
            this.root = item;
            return true;
        }
        while(currItem != null) {
            int comp = currItem.compareTo(item);
            if(comp < 0) currItem = currItem.leftLink;
            if(comp > 0) currItem = currItem.rightLink;
        }
        return false;
    }

    @Override
    public boolean removeItem(ListItem item) {
        return false;
    }

    @Override
    public void traverse(ListItem item) {

    }
}
