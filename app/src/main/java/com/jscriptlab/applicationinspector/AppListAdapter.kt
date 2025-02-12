import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jscriptlab.applicationinspector.ApplicationItem
import com.jscriptlab.applicationinspector.R

class AppListAdapter(private val context: Context, private val appList: List<ApplicationItem>) :
  RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

  class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val appName: TextView = view.findViewById(R.id.appName)
    val packageId: TextView = view.findViewById(R.id.packageId)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_apk, parent, false)
    return AppViewHolder(view)
  }

  override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
    val (name, packageId) = appList[position]
    holder.appName.text = name
    holder.packageId.text = packageId

    // Enable text selection
//    holder.appName.setTextIsSelectable(true)
//    holder.packageId.setTextIsSelectable(true)

    // Copy package ID on item click
    holder.itemView.setOnClickListener {
      copyToClipboard(context, packageId)
      Toast.makeText(context, "Copied: $packageId", Toast.LENGTH_SHORT).show()
    }
  }

  private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Package ID", text)
    clipboard.setPrimaryClip(clip)
  }

  override fun getItemCount() = appList.size
}
