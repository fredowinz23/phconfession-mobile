package com.ph.confession

import android.os.Bundle


class SelectCategoryActivity : BaseActivity() {
    override val TAG = MainActivity::class.java.simpleName
    override val LAYOUT_ID = R.layout.category_list
    override val MAIN_TITLE = "Select a Category"
    override val HAS_FAB = false
    override val HAS_BACK = true

}
