class TodosController < ApplicationController
	skip_before_action :verify_authenticity_token
	def index
		@todos =  Todo.all
		respond_to do |format|
			format.json {render json: @todos}
		end
	end
	def create
		@todo = Todo.new(params.require(:todo).permit([:text, :project_id]))
		if @todo.save
			redirect_to projects_path
		end
	end
	def update
		@todo = Todo.find(params[:id])
		@todo.update(:isCompleted => !@todo.isCompleted)
	end
end
