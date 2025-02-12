package com.jscriptlab.applicationinspector

import AppListAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)

    val searchView = findViewById<SearchView>(R.id.searchView)
    searchView.isIconified = false
    searchView.isSubmitButtonEnabled = false
    searchView.requestFocus()
    // Implement search filtering
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextChange(newText: String?): Boolean {
        filterList(newText)
        return true
      }

      override fun onQueryTextSubmit(query: String?): Boolean {
        return true
      }
    })

    filterList(null)
  }

  private fun filterList(query: String?) {
    var appList = applicationList(packageManager)
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    // Filter the apps in case there is a search
    if(query !== null && !query.trim().isEmpty()) {
      val filteredAppList = mutableListOf<ApplicationItem>()

      for (appItem in appList) {
        val potentialMatchList = listOf(appItem.packageName, appItem.name)
        for(text in potentialMatchList) {
          if(text.trim().lowercase().contains(query.trim().toString().lowercase())) {
            filteredAppList.add(appItem)
            break
          }
        }
      }

      appList = filteredAppList
    }

    val adapter = AppListAdapter(this, appList)
    recyclerView.adapter = adapter
  }

  fun applicationList(packageManager: PackageManager): List<ApplicationItem> {
    val apps = mutableListOf<ApplicationItem>()
    val packages = packageManager.getInstalledApplications(PackageManager.MATCH_ALL)

    for (packageInfo in packages) {
      val name = packageInfo.loadLabel(packageManager).toString()
      val packageName = packageInfo.packageName
      apps.add(ApplicationItem(name, packageName))
    }

    return apps
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

//  override fun onSupportNavigateUp(): Boolean {
//    val navController = findNavController(R.id.nav_host_fragment_content_main)
//    return navController.navigateUp(appBarConfiguration)
//        || super.onSupportNavigateUp()
//  }
}