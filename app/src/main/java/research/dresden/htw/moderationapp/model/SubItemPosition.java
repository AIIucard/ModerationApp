package research.dresden.htw.moderationapp.model;

import android.content.ClipData;

public class SubItemPosition {

    private ItemPosition itemPosition;
    private int position;
    private boolean isColored = false;

    public SubItemPosition(ItemPosition itemPosition, int position) {
        this.itemPosition = itemPosition;
        this.position = position;
    }

    public ItemPosition getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(ItemPosition itemPosition) {
        this.itemPosition = itemPosition;
    }


    @Override
    public boolean equals(Object object)
    {
        boolean isSame = false;

        if (object != null && object instanceof SubItemPosition)
        {
            isSame = ((this.position == ((SubItemPosition) object).position) && (this.itemPosition == ((SubItemPosition) object).itemPosition));
        }

        return isSame;
    }

    public boolean isColored() {
        return isColored;
    }

    public void setColored(boolean colored) {
        isColored = colored;
    }
}
