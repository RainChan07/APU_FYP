//package com.example.ALMD_AppLockforMobileDevice
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CheckBox
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//
//class AppAdapter(context: Context, list: ArrayList<AppInfo>) :
//    RecyclerView.Adapter<AppAdapter.ViewHolder>() {
//    private var mContext: Context? = null
//    private var mDataSet: ArrayList<AppInfo>? = null
//
//    fun AppAdapter(context: Context, list: ArrayList<AppInfo>) {
//        mContext = context
//        mDataSet = list
//    }
//
//    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        var mTextViewLabel: TextView
//        var mTextViewPackage: TextView
//        var mImageViewIcon: ImageView
//        var mAppSelect: CheckBox
//        var mItem: RelativeLayout
//
//        init {
//            // Get the widgets reference from custom layout
//            mTextViewLabel = v.findViewById<View>(R.id.Apk_Name) as TextView
//            mTextViewPackage = v.findViewById<View>(R.id.Apk_Package_Name) as TextView
//            mImageViewIcon = v.findViewById<View>(R.id.packageImage) as ImageView
//            mAppSelect = v.findViewById<View>(R.id.appSelect) as CheckBox
//            mItem = v.findViewById<View>(R.id.item) as RelativeLayout
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): ViewHolder {
//        val v: View =
//            LayoutInflater.from(mContext).inflate(R.layout.mobile_applications_selection, parent, false)
//        return ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        // Get the current package name
//        val packageName = mDataSet!![position].getAppPackage()
//
//        // Get the current app icon
//        val icon = mDataSet!![position].getAppIcon()
//
//        // Get the current app label
//        val label = mDataSet!![position].getAppName()
//
//        // Set the current app label
//        holder.mTextViewLabel.text = label
//
//        // Set the current app package name
//        holder.mTextViewPackage.text = packageName
//
//        // Set the current app icon
//        holder.mImageViewIcon.setImageDrawable(icon)
//        holder.mAppSelect.isChecked = mDataSet!![position].isSelected()
//        holder.mItem.setOnClickListener {
//            mDataSet!![position].setSelected(!mDataSet!![position].isSelected())
//            this@AppAdapter.notifyDataSetChanged()
//        }
//    }
//
//    override fun getItemCount(): Int {
//        // Count the installed apps
//        return mDataSet!!.size
//    }
//
//    init {
//        mContext = context
//        mDataSet = list
//    }
//}