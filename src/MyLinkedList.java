public class MyLinkedList implements NodeList {
    private ListItem root;

    public MyLinkedList(ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return root;
    }

    @Override
    public boolean addItem(ListItem item) {
        ListItem currItem = this.root, prevItem = null;
        if(currItem == null) {
            this.root = item;
            return true;
        }
        do {
            int comp = currItem.compareTo(item);
            if(comp < 0) {
                if(currItem.next() == null) {
                    item.setPrevious(currItem).setNext(item);
                    return true;
                } else currItem = currItem.next();
            }
            else if(comp > 0) {
                if(prevItem == null) {
                    this.root = item;
                    currItem.setPrevious(this.root).setNext(currItem);
                }
                else {
                    item.setNext(currItem).setPrevious(item);
                    item.setPrevious(prevItem).setNext(item);
                }
                return true;
            }
            else return false;
            prevItem = currItem.previous();
        } while(prevItem.next() != null);
        return false;
    }

    public boolean addItem(String val) {
        return this.addItem(new Node(val));
    }

    @Override
    public boolean removeItem(ListItem item) {
        ListItem currItem = this.getRoot();
        do {
            int comp = currItem.compareTo(item);
            if(comp == 0) {
                ListItem prev = currItem.previous();
                ListItem next = currItem.next();
                if(next != null) next.setPrevious(null);
                if(prev != null) prev.setNext(null);
                else this.root = next;
                currItem.setNext(null);
                currItem.setPrevious(null);
                return true;
            }
            else if(comp < 0) currItem = currItem.next();
            else return false;
        } while(currItem != null);
        return false;
    }

    @Override
    public void traverse(ListItem item) {
        do {
            System.out.println(item.value);
            item = item.next();
        } while(item != null);
    }
}
