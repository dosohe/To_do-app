class Todo < ApplicationRecord
	belongs_to :project, optional: true
end
