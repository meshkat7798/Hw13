#!/bin/bash

todo_file="todo.txt"
done_file="done.txt"
deleted_file="deleted.txt"

touch "$todo_file"
touch "$done_file"
touch "$deleted_file"


display_tasks() {
    if [[ -f $1 ]]; then
        echo "Tasks in $2:"
        cat "$1"
    else
        echo "No tasks found in $2."
    fi
}


add_task() {
    echo "$1" >> "$2"
    echo "Task added to $3: $1"
}


move_task() {
    sed -i "/$1/d" "$2"
    echo "$1" >> "$3"
    echo "Task moved from $4 to $5: $1"
}


search_task() {
    if [[ -f $2 ]]; then
        grep -i "$1" "$2"
    else
        echo "No tasks found in $3."
    fi
}


while true; do
    echo " "
    echo "****** TODO LIST SIMULATOR ******"
    echo "1. ADD NEW TASK"
    echo "2. SHOW TASKS TO BE DONE"
    echo "3. SHOW COMPLETED TASKS"
    echo "4. SHOW DELETED TASKS"
    echo "5. MOVE TASK TO COMPLETED LIST"
    echo "6. MOVE TASK TO DELETED LIST"
    echo "7. SEARCH TASKS"
    echo "8. QUIT"
    echo "**********************************"
    echo " "

    read -p "ENTER YOUR CHOICE: " choice

    case $choice in
        1)
            read -p "Enter the task: " new_task
            add_task "$new_task" "$todo_file" "tasks to be done"
            ;;
        2)
            display_tasks "$todo_file" "tasks to be done :"
            ;;
        3)
            display_tasks "$done_file" "completed tasks"
            ;;
        4)
            display_tasks "$deleted_file" "deleted tasks"
            ;;
        5)
            read -p "Enter the task to complete: " task_to_complete
            move_task "$task_to_complete" "$todo_file" "$done_file" "tasks to be done" "completed tasks"
            ;;
        6)
            read -p "Enter the task to delete: " task_to_delete
            move_task "$task_to_delete" "$todo_file" "$deleted_file" "tasks to be done" "deleted tasks"
            ;;
        7)
            read -p "Enter a keyword to search: " keyword
            search_task "$keyword" "$todo_file" "tasks to be done"
            ;;
        8)
            echo "GOODBYE!"
            exit 0
            ;;
        *)
            echo "INVALID CHOICE. PLEASE TRY AGAIN !"
            ;;
    esac
done