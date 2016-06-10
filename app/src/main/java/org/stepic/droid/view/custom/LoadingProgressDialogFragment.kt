package org.stepic.droid.view.custom

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class LoadingProgressDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable=false
        return LoadingProgressDialog(context)
    }

    companion object {

        fun newInstance(): DialogFragment {

            val args = Bundle()

            val fragment = LoadingProgressDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
