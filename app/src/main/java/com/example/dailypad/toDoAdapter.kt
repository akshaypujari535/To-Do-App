package com.example.dailypad

import android.app.AlertDialog
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class toDoAdapter( private val clickListener : (Note) -> Unit,
                     private val deleteListener:(Note) -> Unit):ListAdapter<Note,toDoAdapter.toDoViewHolder>(DiffCallback()){
    inner class toDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.textTitle)
        private val summary = itemView.findViewById<TextView>(R.id.summary)
        private val createddate = itemView.findViewById<TextView>(R.id.createddate)
        private val duedate = itemView.findViewById<TextView>(R.id.dueDate)
        private val checkbox = itemView.findViewById<CheckBox>(R.id.checkComplete)
        private val delete = itemView.findViewById<ImageButton>(R.id.delete)

        private var countDownTimer: CountDownTimer? = null

        fun bind(note: Note) {
          itemView.setOnClickListener{
              clickListener(note)
          }
            delete.setOnClickListener{
                deleteListener(note)
            }
            title.text = note.title
            summary.text = note.desc
            createddate.text = note.createdDate.toString()

            if (countDownTimer != null) {
                countDownTimer?.cancel()
                countDownTimer = null
            }

            val currentTimeMillis = System.currentTimeMillis()
            val dueTimeMillis = note.duedate.time
            val countdownTimeMillis = dueTimeMillis - currentTimeMillis

            countDownTimer = object : CountDownTimer(countdownTimeMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000
                    val days = seconds / (24 * 60 * 60)
                    val hours = (seconds % (24 * 60 * 60)) / (60 * 60)
                    val minutes = ((seconds % (24 * 60 * 60)) % (60 * 60)) / 60

                    val countdownText = String.format(
                        "%02dd %02dh %02dm",
                        days, hours, minutes
                    )
                    duedate.text = countdownText
                }

                override fun onFinish() {
                    duedate.text = "Expired"
                }
            }
            countDownTimer?.start()

            checkbox.isChecked = note.isCompleted

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): toDoViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return toDoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: toDoViewHolder, position: Int) {
      val currenttodo=getItem(position)
        holder.bind(currenttodo)

    }

    private class DiffCallback:DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem==newItem
        }
    }

}