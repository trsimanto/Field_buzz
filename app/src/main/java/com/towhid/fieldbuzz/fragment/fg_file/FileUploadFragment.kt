package com.towhid.fieldbuzz.fragment.fg_file

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.towhid.fieldbuzz.R
import com.towhid.fieldbuzz.fragment.fg_success.SuccessFragment
import com.towhid.fieldbuzz.network.api.RetrofitClient
import com.towhid.fieldbuzz.network.error.ErrorUtils2.parseError
import com.towhid.fieldbuzz.network.model.file.FileRes
import com.towhid.fieldbuzz.shared_pref.SharedPref
import com.towhid.fieldbuzz.util.FileUtils
import com.towhid.fieldbuzz.util.LoginStatus
import com.towhid.fieldbuzz.util.Utility
import kotlinx.android.synthetic.main.fragment_file_upload.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FileUploadFragment : Fragment() {
    private val PICK_PDF_REQUEST = 1
    lateinit var sharedPref: SharedPref
    lateinit var mContext: Context
    lateinit var progress_lay: RelativeLayout
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_file_upload, container, false)
        init(rootView)
        action(rootView)
        return rootView
    }

    private fun init(rootView: View) {
        sharedPref = SharedPref(mContext)
        progress_lay = rootView.findViewById(R.id.progress_lay)
        progress_lay.setOnClickListener {}
    }

    private fun action(rootView: View) {
        rootView.file_picker.setOnClickListener {permissionUpload()}
        rootView.browse.setOnClickListener {permissionUpload()}
    }

    private fun permissionUpload() {
        if (ContextCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    )
            ) {
                ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        101
                )
                sharedPref.setDeny(true)
            } else {
                if (sharedPref.getDeny()) showSettingsDialog() else {
                    ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            101
                    )
                }
            }
        } else {
            openFileChooser()
        }

    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Need Permissions")
        builder.setMessage("Field Buzz app needs permission to file upload feature. You can grant it in app settings.")
        builder.setPositiveButton(
                "GOTO SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(
                "Cancel"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(
                "package",
                context!!.packageName,
                null
        )
        intent.data = uri
        activity!!.startActivityForResult(intent, 101)
    }

    private fun openFileChooser() {
        var intent = Intent()
      //  var mimeTypes = arrayOf("*/pdf")
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
       // intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_PDF_REQUEST, Bundle())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null
        ) {
            pdfUpload(data.data!!)
        }
    }

    private fun pdfUpload(uri: Uri) {
        progress_lay.visibility = View.VISIBLE
        var file: File = File(FileUtils.getPath(context!!, uri))
        var requestBody: RequestBody = file.asRequestBody("*/pdf".toMediaTypeOrNull())
        var part: MultipartBody.Part =
                MultipartBody.Part.createFormData("pdf", file.name, requestBody)

        val call: Call<FileRes> = RetrofitClient.getInstance().getApi().uploadfile(
                sharedPref.fileTokenId,
                sharedPref.token!!,
                part
        )
        call.enqueue(object : Callback<FileRes> {
            override fun onResponse(call: Call<FileRes>, response: Response<FileRes>) {
                progress_lay.visibility = View.GONE
                if (response.isSuccessful) {
                    SharedPref(mContext).loginStatus = LoginStatus.success.toString()
                    Utility.replaceFramgentWithoutBackStack(activity, SuccessFragment.newInstance(true))
                } else {
                    try {
                        Toast.makeText(mContext, parseError(response).getMassage(), Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(mContext, "File is not upload", Toast.LENGTH_LONG).show();
                    }

                }

            }

            override fun onFailure(call: Call<FileRes>, t: Throwable) {
                progress_lay.visibility = View.GONE
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                FileUploadFragment().apply {}
    }
}