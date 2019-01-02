Rails.application.routes.draw do

  get 'todos/index'

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  resources :todos

  post 'todos/:id'=> 'todos#update'

  get 'get_json'=> 'todos#get_projects'

  root 'todos#index'
end
