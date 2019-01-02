class TodosController < ApplicationController

protect_from_forgery except: [:update, :create]
  def index
    @projects = Project.all
    @todos = Todo.all
    @todo = Todo.new
  end

  def create
    @todo = Todo.create(todo_params)
    @todo.isCompleted = false
    @todo.save
    redirect_to root_path
  end

  def update
    @todo = Todo.find(params[:id])
    @todo.update(:isCompleted => !@todo.isCompleted)
    redirect_to root_path
  end

  def get_projects
  render json: Project.all.to_json(:include => :todos)
  end
end

private
  def todo_params
    params.require(:todo).permit(:text, :isCompleted, :project_id)
  end
