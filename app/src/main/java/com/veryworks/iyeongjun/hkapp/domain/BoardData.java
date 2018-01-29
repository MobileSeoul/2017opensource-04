package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class BoardData
{
    private BoardItem[] boardItem;

    public BoardItem[] getBoardItem ()
    {
        return boardItem;
    }

    public void setBoardItem (BoardItem[] boardItem)
    {
        this.boardItem = boardItem;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [boardItem = "+boardItem+"]";
    }
}

