class TodosController < ApplicationController
  def index
    @projects = Project.all
    @todos = Todo.all
    @todo = Todo.new
  end

  def create
    @todo = Todo.create(todo_params)

    redirect_to root_path
  end

  private
    def todo_params
      params.require(:todo).permit(:text, :isCompleted, :project_id)
    end

  def update
  end
end
