package jp.panta.navigationtodoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import jp.panta.navigationtodoapp.databinding.FragmentTodosBinding
import jp.panta.navigationtodoapp.view.TaskListAdapter
import jp.panta.navigationtodoapp.viewmodel.TodosViewModel


class TasksFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            TasksFragment()
    }

    private lateinit var mBinding: FragmentTodosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_todos, container, false)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val app = requireContext().applicationContext
        val todoApplication = app as TodoApplication

        val viewModel = ViewModelProvider(this, TodosViewModel.Factory(todoApplication))[TodosViewModel::class.java]

        val taskListAdapter = TaskListAdapter(viewModel, viewLifecycleOwner)

        mBinding.todoList.adapter = taskListAdapter
        mBinding.todoList.layoutManager = LinearLayoutManager(mBinding.root.context)

        viewModel.todos.observe(viewLifecycleOwner){ list ->
            taskListAdapter.submitList(list)
        }
    }


}
