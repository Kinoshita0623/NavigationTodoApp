package jp.panta.navigationtodoapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.panta.navigationtodoapp.R
import jp.panta.navigationtodoapp.databinding.ItemTaskBinding
import jp.panta.navigationtodoapp.model.Task
import jp.panta.navigationtodoapp.viewmodel.TodosViewModel


class TaskListAdapter(
    private val todosViewModel: TodosViewModel,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(DiffItemCallback()){

    class DiffItemCallback : DiffUtil.ItemCallback<Task>(){
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.todoId == newItem.todoId
        }
    }

    class ViewHolder(
        private val binding: ItemTaskBinding,
        private val todosViewModel: TodosViewModel,
        private val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(task: Task){
            binding.task = task
            binding.tasksViewModel = todosViewModel
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = DataBindingUtil.inflate<ItemTaskBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_task,
            parent,
            false
        )

        return ViewHolder(inflater, todosViewModel = todosViewModel, lifecycleOwner)

    }
}