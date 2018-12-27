puts "Seed Data"


seed_file = Rails.root.join('db', 'seeds', 'seeds.yml')
config = YAML::load_file(seed_file)
# puts(config)

num=0
config["projects"].each do |project|
        num+=1
        Project.create!(title: project["title"])
        # puts project["title"]

        # get to the todos…
        project["todos"].each do |todo|
              Todo.create!(text: todo["text"],isCompleted:todo["isCompleted"],project_id: num)
        end

end
