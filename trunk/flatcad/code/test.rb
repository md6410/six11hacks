require 'sketchup.rb'

def drawStuff
	model = entities
	ents=model.entities
end

if( not file_loaded?("gabe.rb") )
	add_separator_to_menu("Plugins")
	plugins_menu = UI.menu("Plugins")
	plugins_menu.add_item("Gabe") { drawStuff }
end

	file_loaded("gabe.rb")
