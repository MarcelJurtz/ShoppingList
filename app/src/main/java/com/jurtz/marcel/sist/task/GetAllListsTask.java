package com.jurtz.marcel.sist.task;

import android.os.AsyncTask;

import com.jurtz.marcel.sist.contract.IListPresenter;
import com.jurtz.marcel.sist.database.ShoppingListDao;
import com.jurtz.marcel.sist.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class GetAllListsTask extends AsyncTask<Void, Void, Void> {

    private final ShoppingListDao _listDao;
    private final IListPresenter _presenter;

    List<ShoppingList> lists = new ArrayList<ShoppingList>();

    public GetAllListsTask(IListPresenter presenter, ShoppingListDao dao) {
        super();
        _listDao = dao;
        _presenter = presenter;
    }

    protected Void doInBackground(Void... params) {
        lists = _listDao.getAll();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        _presenter.refresh(lists);

        //shoppingLists = lists;
        //refreshGui();
    }
}
