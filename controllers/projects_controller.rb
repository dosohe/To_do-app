class ProjectsController < ApplicationController
	skip_before_action :verify_authenticity_token
	def index
		@projects = Project.all
		@todo = Todo.new
		respond_to do |format|
			format.html
			format.json {render json: @projects}
		end
	end
	def create

	end
	def update

	end
end
