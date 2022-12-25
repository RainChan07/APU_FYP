package com.example.ALMD_AppLockforMobileDevice

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MobileApplicationsActivity : AppCompatActivity() {

    val lockedAppsList: MutableList<String> = ArrayList()

    private var allApps: List<AppList>? = null
    private var appAdapter: AppAdapter? = null
    var allAppsList: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mobile_applications_selection)

        val sharedPref_masterPin = getSharedPreferences("masterPin", MODE_PRIVATE)
        val master_Pin: String? = sharedPref_masterPin.getString("masterPIN", null)

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val lockedApps_List = sharedPref_lockedAppsList.getStringSet("lockedAppsList", setOf<String>())?.toMutableList()
        val editor_lockedAppsList = sharedPref_lockedAppsList.edit()

//        this.cacheDir.deleteRecursively()

        displayAllApps(view = null)

        val mobileApplicationsSelectionBackBtn = findViewById<Button>(R.id.mobileApplicationsSelection_backBtn)
        mobileApplicationsSelectionBackBtn.setOnClickListener {
            if ((master_Pin != null) && (lockedAppsList != null)) {
                val toMainMenuActivity = Intent(this, MainMenuActivity::class.java)
                startActivity(toMainMenuActivity)
            } else {
                val toMasterPINActivity = Intent(this, MasterPINActivity::class.java)
                startActivity(toMasterPINActivity)
            }
        }

        val mobileApplicationsSelectionNextBtn = findViewById<Button>(R.id.mobileApplicationsSelection_nextBtn)
        mobileApplicationsSelectionNextBtn.setOnClickListener (View.OnClickListener{
            editor_lockedAppsList.putStringSet("lockedAppsList", lockedAppsList.toSet())
            editor_lockedAppsList.apply()

            if ((master_Pin != null) && (lockedAppsList != null)) {
                val toMainMenuActivity = Intent(this, MainMenuActivity::class.java)
                startActivity(toMainMenuActivity)
            } else {
                Toast.makeText(applicationContext, "Please select at least 1 application to lock", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
        })
    }

    fun displayAllApps(view: View?) {
        allAppsList = findViewById<ListView>(R.id.all_apps_list)
        allApps = getAllApps()
        appAdapter = AppAdapter(this@MobileApplicationsActivity, allApps)
        allAppsList!!.adapter = appAdapter

        //Total Number of Installed-Apps(i.e. List Size)
        val totalApps: String = allAppsList!!.count.toString() + ""
        val countApps: TextView = findViewById<TextView>(R.id.countApps)
        countApps.text = "Total apps: $totalApps"
    }

    private fun getAllApps(): List<AppList> {
        val intent: Intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val pm: PackageManager = packageManager
        val apps: MutableList<AppList> = ArrayList()
        val packs: List<ResolveInfo> = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA)

        for (i in packs.indices) {
            val r: ResolveInfo = packs[i]
            r.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
            if (r.loadLabel(packageManager).toString() == "ALMD")
            {
                //Do nothing when the app itself is iterated through the package list, so that user is unable to choose whether they want to lock the app itself or not
            } else {
                val appName: String = r.loadLabel(packageManager).toString()
                val icon: Drawable = r.loadIcon(packageManager)
                apps.add(AppList(appName, icon))
            }
        }
        return apps.sortedWith(compareBy { it.getName() })
    }

    inner class AppAdapter(context: Context, var listStorage: List<AppList>?) : BaseAdapter() {
        var layoutInflater: LayoutInflater

        var checkedState = BooleanArray(listStorage!!.size) {false}

        init {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getCount(): Int {
            return listStorage!!.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var convertView: View? = convertView
            var viewHolder: ViewHolder
//            var checkedState = BooleanArray(listStorage!!.size) { false }

            if (convertView == null) {
                viewHolder = ViewHolder()
                convertView = layoutInflater.inflate(R.layout.displayed_applications, parent, false)
                viewHolder.textInListView = convertView.findViewById(R.id.list_app_name) as TextView
                viewHolder.imageInListView = convertView.findViewById(R.id.app_icon) as ImageView
                viewHolder.checkBoxInListView = convertView.findViewById(R.id.toggleLock) as CheckBox
                convertView.tag = viewHolder // For scrolling
            } else {
                viewHolder = convertView.tag as ViewHolder
            }

            viewHolder.checkBoxInListView!!.tag = position
            viewHolder.checkBoxInListView!!.isChecked = checkedState[position]

            viewHolder.checkBoxInListView!!.setOnClickListener {
                val isChecked: Boolean = viewHolder.checkBoxInListView!!.isChecked
                checkedState[position] = isChecked
                if (isChecked) {
                    lockedAppsList.add(viewHolder.textInListView!!.text as String)
                } else {
                    lockedAppsList.remove(viewHolder.textInListView!!.text as String)
                }
            }

            viewHolder.textInListView!!.text = listStorage!![position].getName()
            viewHolder.imageInListView!!.setImageDrawable(listStorage!![position].getIcon())
//            viewHolder.checkBoxInListView!!.isChecked = listStorage!![position].isSelected()!! // Kept crashing
            return convertView!!
        }

        internal inner class ViewHolder {
            var textInListView: TextView? = null
            var imageInListView: ImageView? = null
            var checkBoxInListView: CheckBox? = null
        }
    }

    inner class AppList(private var name: String, icon: Drawable) {
    var icon: Drawable
    private var selected: Boolean? = null

        init {
            this.name = name
            this.icon = icon
        }

        fun getName(): String {
            return name
        }

        @JvmName("getIcon1")
        fun getIcon(): Drawable {
            return icon
        }

        fun isSelected(): Boolean? {
            return selected
        }

        fun setSelected(checked: Boolean) {
            this.selected = selected
        }
    }

    override fun onBackPressed() {
        return
    }
}