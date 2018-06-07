package research.dresden.htw.moderationapp.model;

import android.content.ClipData;

public class ItemPosition {

    private int position;
    private boolean isColored = false;

    public ItemPosition(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean isSame = false;

        if (object != null && object instanceof ItemPosition)
        {
            isSame = this.position == ((ItemPosition) object).position;
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
