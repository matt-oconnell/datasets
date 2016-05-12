import json
import sys
from pprint import pprint

class JsonWalker(object):
    def __init__(self, name, comment_dictionary):
        self.dictionaries = {}
        self.lists = {}
        self.path = []
        self.name = name
        self.comment_dictionary = comment_dictionary
    
    @property
    def json_path(self):
        return ".".join([self.name]+self.path)
        
    def type_check(self, value):
        return value.__class__.__name__
        
    def walk(self, chunk, parent_name):
        if isinstance(chunk, dict):
            self.walk_dict(chunk, parent_name)
        elif isinstance(chunk, list):
            self.walk_list(chunk, parent_name)
        else:
            self.walk_atomic(chunk, parent_name)
        return self
        
    def walk_dict(self, a_dict, parent_name):
        self.dictionaries[self.json_path] = {'fields': [], 'name': parent_name}
        for key, value in a_dict.items():
            self.path.append(key)
            dictionary = {'type': self.type_check(value),
                          'key': key,
                          'example': value,
                          'path': self.json_path,
                          'comment': self.comment_dictionary.get(self.json_path, "")}
            self.walk(value, key)
            self.path.pop()
            self.dictionaries[self.json_path]['fields'].append(dictionary)
    def walk_list(self, a_list, parent_name):
        assert a_list, "Empty list at {path}".format(path=self.json_path)
        entry_path = self.json_path
        self.path.append("[0]")
        first = a_list[0]
        self.lists[entry_path] = {'type': self.type_check(first), 
                                  'example': first, 
                                  'comment': self.comment_dictionary.get(self.json_path, ""), 
                                  'path': self.json_path}
        self.walk(first, parent_name)
        self.path.pop()
    def walk_atomic(self, an_atomic, parent_name):
        pass

DICTIONARIES = set()
LISTS = set()

def is_leaf_type(a_type):
    return a_type in ('int', 'float', 'bool', 'str')

def sluggify(astr):
    return "-".join(astr.split())

def deep_type(data, optional_type=""):
    if isinstance(data, dict):
        return optional_type
    elif isinstance(data, list):
        if isinstance(data[0], dict):
            label = " ".join(data[0].keys())
        else:
            label = deep_type(data[0])
        return 'List[{}]'.format(label)
    elif isinstance(data, unicode):
        return 'str'
    else:
        return data.__class__.__name__

def guess_schema(key, input):
    if isinstance(input, dict):
        DICTIONARIES.add(
            tuple(
                [key, tuple([(k, deep_type(v, k)) for k,v in input.items()])]
            )
        )
        return {str(key.encode('ascii', 'replace').decode('ascii')): 
                guess_schema(key, value) for key, value in input.items()}
    elif isinstance(input, list):
        if isinstance(input[0], dict):
            label = " ".join(input[0].keys())
        else:
            label = str(len(input))
        LISTS.add(
            deep_type(input[0], label)
        )
        return [guess_schema(label, input[0])] if input else []
    else:
        return deep_type(input)

if __name__ == '__main__':
    filename = sys.argv[1]
    with open(filename) as json_file:
        data = json.load(json_file)
    walker = JsonWalker()
    walker.walk(data)
    pprint(walker.lists)
    pprint(walker.dictionaries)
    '''
    pprint(guess_schema(filename.split('.')[-2], json.load(open(filename))))
    for label, fields in DICTIONARIES:
        print "<div id='dialog-dict-{0}' title='Dict ({1} keys)'>".format(sluggify(label), len(fields))
        print "<table class='table table-condensed table-striped table-bordered'>"
        print " <tr> <th>Key</th> <th>Type</th> <th>Example Value</th> <th>Comments</th> </tr>"
        for name, type in fields:
            print " <tr>"
            print "   <td>{key}</td>".format(key=name)
            print "   <td>{type}</td>".format(type=type)
            print "   <td></td>".format()
            print "   <td></td>".format()
            print " </tr>"
        print "</table>"
        print "</div>"
    for type in LISTS:
        print "<div id='dialog-list-{0}' title='List'>".format(sluggify(type))
        print "<table class='table table-condensed table-striped table-bordered'>"
        print " <tr> <th>Index</th> <th>Type</th> <th>Example Value</th> <th>Comments</th> </tr>"    
        print " <tr>"
        print "   <td>0</td>".format(key=name)
        print "   <td>{type}</td>".format(type=type)
        print "   <td></td>".format()
        print "   <td></td>".format()
        print " </tr>"
        print "</table>"
        print "</div>"
    '''
    
# Actual type of dict leaves
# Json paths