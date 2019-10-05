package com.jurtz.marcel.sist.task;

import android.os.AsyncTask;

import com.jurtz.marcel.sist.contract.IListPresenter;
import com.jurtz.marcel.sist.database.ShoppingListDao;
import com.jurtz.marcel.sist.model.ShoppingList;

public class AddListTask extends AsyncTask<Void, Void, Void> {

    private final ShoppingListDao _listDao;
    private final ShoppingList _list;
    private  final IListPresenter _presenter;

    public AddListTask(IListPresenter presenter, ShoppingListDao dao, ShoppingList list) {
        super();

        _listDao = dao;
        _list = list;
        _presenter = presenter;
    }

    protected Void doInBackground(Void... params) {
        _listDao.insertList(_list);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        new GetAllListsTask(_presenter, _listDao).execute(); // shoppingListDao
    }
}
