package owner.credoadmins.com.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import java.util.Arrays
import java.util.LinkedList

class MultiSelectSpinner : android.support.v7.widget.AppCompatSpinner, DialogInterface.OnMultiChoiceClickListener
{
    private var listener: OnMultipleItemsSelectedListener? = null
    private var _items: Array<String>? = null
    private var mSelection: BooleanArray? = null
    private var mSelectionAtStart: BooleanArray? = null
    private var _itemsAtStart: String? = null
    private var c: Context? = null
    private val simple_adapter: ArrayAdapter<String>
    private var hasNone = false

    private val selectedStrings: List<String>
        get() {
            val selection = LinkedList<String>()
            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    selection.add(_items!![i])
                }
            }
            return selection
        }

    val selectedIndices: List<Int>
        get() {
            val selection = LinkedList<Int>()
            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    selection.add(i)
                }
            }
            return selection
        }

    private val selectedItemsAsString: String
        get() {
            val sb = StringBuilder()
            var foundOne = false

            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    if (foundOne) {
                        sb.append(", ")
                    }
                    foundOne = true

                    sb.append(_items!![i])


                }
            }
            return sb.toString()
        }

    interface OnMultipleItemsSelectedListener {
        fun selectedIndices(indices: List<Int>)
        fun selectedStrings(strings: List<String>)
    }

    constructor(context: Context) : super(context) {
        c = context
        simple_adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        super.setAdapter(simple_adapter)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        simple_adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        super.setAdapter(simple_adapter)
    }

    fun setListener(listener: OnMultipleItemsSelectedListener) {
        this.listener = listener
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if (mSelection != null && which < mSelection!!.size) {
            if (hasNone) {
                if (which == 0 && isChecked && mSelection!!.size > 1) {
                    for (i in 1 until mSelection!!.size) {
                        mSelection!![i] = false
                        (dialog as AlertDialog).listView.setItemChecked(i, false)
                    }
                } else if (which > 0 && mSelection!![0] && isChecked) {
                    mSelection!![0] = false
                    (dialog as AlertDialog).listView.setItemChecked(0, false)
                }
            }
            mSelection!![which] = isChecked
            simple_adapter.clear()
            simple_adapter.add(buildSelectedItemString())
        } else {
            throw IllegalArgumentException(
                "Argument 'which' is out of bounds."
            )
        }
    }

    override fun performClick(): Boolean {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Please select!")
        builder.setMultiChoiceItems(_items, mSelection, this)
        _itemsAtStart = selectedItemsAsString
        //        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                setSelection(0);
        //            }
        //        });
        builder.setPositiveButton("Submit") { dialog, which ->
            val selectedSubjectsList = selectedIndices

            val s = selectedSubjectsList.size



            if (s > 0) {
                Log.d(
                    "Selected",
                    " at least one language          " + Arrays.toString(mSelection) + "      " + Arrays.toString(
                        mSelectionAtStart
                    )
                )
                System.arraycopy(mSelection!!, 0, mSelectionAtStart, 0, mSelection!!.size)

                listener!!.selectedIndices(selectedIndices)
                listener!!.selectedStrings(selectedStrings)
            } else {
                setSelection(0)
                //                    Toast.makeText(c, "Please Select at least one language", Toast.LENGTH_SHORT).show();
                Log.d("Please Select", " at least one language")
                System.arraycopy(mSelection!!, 0, mSelectionAtStart, 0, mSelection!!.size)

                listener!!.selectedIndices(selectedIndices)
                listener!!.selectedStrings(selectedStrings)
                //                    return;
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            simple_adapter.clear()
            simple_adapter.add(_itemsAtStart)
            System.arraycopy(mSelectionAtStart!!, 0, mSelection, 0, mSelectionAtStart!!.size)
        }
        builder.show()
        builder.setCancelable(false)
        return true
    }

    override fun setAdapter(adapter: SpinnerAdapter) {
        throw RuntimeException("setAdapter is not supported by MultiSelectSpinner.")
    }


    fun setItems(items: Array<String>) {

        _items = items
        mSelection = BooleanArray(_items!!.size)
        mSelectionAtStart = BooleanArray(_items!!.size)
        simple_adapter.clear()
        simple_adapter.add(_items!![0])
        Arrays.fill(mSelection, false)
        mSelection!![0] = true
        mSelectionAtStart!![0] = true
    }

    fun setItems(items: List<String>) {
        _items = items.toTypedArray()
        mSelection = BooleanArray(_items!!.size)
        mSelectionAtStart = BooleanArray(_items!!.size)
        simple_adapter.clear()
        simple_adapter.add(_items!![0])
        Arrays.fill(mSelection, false)
        mSelection!![0] = true
    }

    fun setSelection(selection: Array<String>) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        for (cell in selection) {
            for (j in _items!!.indices) {
                if (_items!![j] == cell) {
                    mSelection!![j] = true
                    mSelectionAtStart!![j] = true
                }
            }
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    fun setSelection(selection: List<String>) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        for (sel in selection) {
            for (j in _items!!.indices) {
                if (_items!![j] == sel) {
                    mSelection!![j] = true
                    mSelectionAtStart!![j] = true
                }
            }
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }


    override fun setSelection(index: Int) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        if (index >= 0 && index < mSelection!!.size) {
            mSelection!![index] = true
            mSelectionAtStart!![index] = true
        } else {
            throw IllegalArgumentException(
                "Index " + index
                        + " is out of bounds."
            )
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    fun setSelection(selectedIndices: IntArray) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        for (index in selectedIndices) {
            if (index >= 0 && index < mSelection!!.size) {
                mSelection!![index] = true
                mSelectionAtStart!![index] = true
            } else {
                throw IllegalArgumentException(
                    "Index " + index
                            + " is out of bounds."
                )
            }
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    private fun buildSelectedItemString(): String {
        val sb = StringBuilder()
        var foundOne = false

        for (i in _items!!.indices) {
            if (mSelection!![i]) {
                if (foundOne) {
                    sb.append(", ")
                }
                foundOne = true
                sb.append(_items!![i])
            }
        }
        return sb.toString()
    }

    fun hasNoneOption(`val`: Boolean) {
        hasNone = `val`
    }
}
