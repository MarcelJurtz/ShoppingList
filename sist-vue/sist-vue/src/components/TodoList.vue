<template>
    <div>
        <input type="text" class="todo-input" placeholder="What needs to be done?" v-model="newTodo" @keyup.enter="addTodo">

        <div class="menu">
            <div><label><input type="checkbox" :checked="!anyRemaining" @change="checkAll">Mark all as completed</label></div>
            <div>{{ remaining }} items remaining</div>
        </div>
        <div class="filter">
            <div>
                <label>Filter:</label>
                <button :class="{ active: filter=='all' }" @click="filter ='all'">All</button>
                <button :class="{ active: filter=='active' }" @click="filter ='active'">Active</button>
                <button :class="{ active: filter=='completed' }" @click="filter ='completed'">Completed</button>
            </div>
            <transition name="fade">
                <div v-if="anyCompleted">
                    <button @click="clearCompleted">Clear Completed</button>
                </div>
            </transition>
        </div>

        <transition-group name="fade" enter-active-class="animated fadeInUp" leave-active-class="animated fadeOutDown">

        <div v-for="(todo, index) in todosFiltered" :key="todo.id" class="todo-item">
            <div class="todo-item-title">
                <input type="checkbox" v-model="todo.completed">
                <div v-if="!todo.editing" @dblclick="editTodo(todo)" class="label" :class="{completed: todo.completed}">{{ todo.task }}</div>
                <input v-else class="edit" type="text" v-model="todo.task" @blur="endEdit(todo)" @keyup.enter="endEdit(todo)" @keyup.esc="cancelEdit(todo)" v-focus>
            </div>
            <div class="todo-item-remove" @click="removeTodo(index)">
                &times;
            </div>
        </div>

        </transition-group>
    </div>
</template>

<script>
export default {
  name: 'todo-list',
  data () {
    return {
      filter: "all",
      idForTodo: 4,
      newTodo: '',
      todos: [
        {
            'id': 1,
            'task': 'Clean Appartment',
            'completed': false,
            'editing': false
        },
        {
            'id': 2,
            'task': 'Shop groceries',
            'completed': false,
            'editing': false
        },
        {
            'id': 3,
            'task': 'Train hard :)',
            'completed': false,
            'editing': false
        }]
    }
  },
  methods: {
      addTodo() {
          if(this.newTodo.trim().length == 0)
            return;

          this.todos.push({
              id: this.idForTodo,
              task: this.newTodo, // Two-way databinding from input-model
              completed: false,
              editing: false
          });

          this.newTodo = '';
          this.idForTodo++;
      },
      removeTodo(index) {
          this.todos.splice(index, 1);
      },
      editTodo(todo) {
          todo.cache = todo.task;
          todo.editing = true;
      },
      endEdit(todo) {
          if(todo.task.trim().length == 0)
            todo.task = todo.cache;

          todo.cache = '';
          todo.editing = false;
      },
      cancelEdit(todo) {
          todo.task = todo.cache;
          todo.editing = false;
      },
      checkAll() {
          const markCompleted = this.anyRemaining;
          this.todos.forEach(todo => todo.completed = markCompleted);
      },
      clearCompleted() {
          this.todos = this.todos.filter(todo => !todo.completed);
      }
  },
  computed: {
      remaining() {
          return this.todos.filter(todo => !todo.completed).length;
      },
      anyRemaining() {
          return this.remaining > 0;
      },
      anyCompleted() {
          return this.todos.filter(todo => todo.completed).length > 0;
      },
      todosFiltered() {
          if(this.filter == "active") 
            return this.todos.filter(todo => !todo.completed);
          else if(this.filter == "completed") 
            return this.todos.filter(todo => todo.completed);
          else 
            return this.todos;
      }
  },
  directives: {
      focus: {
          inserted: function(el) {
              el.focus(); // Auto Focus input when becoming visible
          }
      }
  }
}
</script>

<style lang="less">

// Animate CSS
@import 'https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css';

.todo-input {
    width: 100%;
    padding: 10px 18px;
    font-size: 18px;
    margin-bottom: 16px;

    :focus {
        outline: 0;
    }
}

.todo-item {
    margin-bottom: 12px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    animation-duration: 0.3s;

    .todo-item-title {

        display: flex;
        align-items: center;

        .label, .edit {
            padding: 10px;
            margin-left: 12px;
        }

        .label {         
            border: 1px solid white;     
        }

        .edit {
            font-size: 20px;
            color: #2C3E50;
            width: 100%;
            border: 1px solid #CCC;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .completed {
            text-decoration: line-through;
            color: grey;
        }
    }

    .todo-item-remove {
        cursor: pointer;
        margin-left: 14px;
        
        :hover {
            color:black;
        }
    }
}

.menu, .filter {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 16px;
    border-top: 1px solid lightgray;
    padding-top: 14px;
    margin-bottom: 14px;

    button {
        font-size: 14px;
        background-color: white;
        appearance: none;

        :hover {
            background: gray;
        }

        :focus {
            outline: none;
        }
    }

    .active  {
        background: darkgray;
    }
}

    // Transitions
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
        opacity: 0;
    }
</style>