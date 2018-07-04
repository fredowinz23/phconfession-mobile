package com.ph.confession


class WriteConfessionActivity : BaseActivity() {
    override val TAG = MainActivity::class.java.simpleName
    override val LAYOUT_ID = R.layout.activity_write_confession
    override val MAIN_TITLE = "Write a Confession"
    override val HAS_FAB = false
    override val HAS_BACK = true

}
