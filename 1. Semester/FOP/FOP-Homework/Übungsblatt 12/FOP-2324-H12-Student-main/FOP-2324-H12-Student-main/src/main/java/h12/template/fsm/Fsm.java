package h12.template.fsm;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Representing an Automata
 */
public class Fsm implements Iterable<State>{

    private final Set<State> states = new HashSet<>();
    private State initialState = null;

    /**
     * Adds a state to Automata
     * @param state State which get added
     */
    public void addState(final State state){
        states.add(state);
    }

    /**
     * Set Initial State of Automata
     * @param state The initial State of Automata
     */
    public void setInitialState(final State state){
        initialState = state;
    }

    /**
     *
     * @return the initial State
     */
    public State getInitialState(){
        return initialState;
    }

    /**
     * Creates a new Instance of this {@link Fsm}
     * @return the generated {@link FsmInstance}
     */
    public FsmInstance createInstance(){
        return new FsmInstance(this, initialState != null ? initialState : states.iterator().next());
    }

    /**
     *
     * @return the number of states in this automata
     */
    public int getNumberOfStates(){
        return states.size();
    }

    @NotNull
    @Override
    public Iterator<State> iterator() {
        return states.iterator();
    }

    /**
     * Check if the {@link Fsm} is verbose. Which means, it is fully moore
     * @return true, iff it is verbose
     */
    public boolean isVerbose(){
        final HashMap<State, Set<BitField>> outputOfState = new HashMap<>();
        forEach(state -> outputOfState.put(state, new HashSet<>()));

        forEach(from -> from.forEach(transition -> outputOfState.get(transition.getNextState()).add(transition.getOutput())));

        for(final var outputs : outputOfState.values()){
            if(outputs.size() > 1){ // if there is more than one output combination, this breaks verbosity
                return false;
            }
        }

        return true;
    }

    /**
     * Pair of State and Output
     */
    private static class StateAndOutput {
        private final State originalState;
        private final BitField output;

        /**
         * Create new Pair
         * @param state The state
         * @param output The output of state
         */
        public StateAndOutput(final State state, final BitField output){
            originalState = state;
            this.output = output;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final StateAndOutput that = (StateAndOutput) o;
            return Objects.equals(originalState, that.originalState) && Objects.equals(output, that.output);
        }

        @Override
        public int hashCode() {
            return Objects.hash(originalState, output);
        }
    }


    /**
     * Converts this {@link Fsm} to a verbose {@link Fsm}
     * @return the verbose Automata
     */
    public Fsm toVerboseFsm(){
        // Collect output of state
        final HashMap<State, Set<BitField>> outputOfState = new HashMap<>();
        forEach(state -> outputOfState.put(state, new HashSet<>()));
        forEach(from -> from.forEach(transition -> outputOfState.get(transition.getNextState()).add(transition.getOutput())));

        final Fsm newFsm = new Fsm();


        // Make new states
        final HashMap<StateAndOutput, State> newChangedStateMap = new HashMap<>();
        final HashMap<State, State> newNormalStateMap = new HashMap<>();

        for(final var entry : outputOfState.entrySet()){
            final State originalState = entry.getKey();
            if(entry.getValue().size() > 1){
                // needs to be changed
                for(final var output : entry.getValue()){
                    final State newState = new State(originalState.getName() + "__" + output.toString('X'));
                    newChangedStateMap.put(new StateAndOutput(originalState, output), newState);
                    newFsm.addState(newState);
                }
            }else{
                final State newState = new State(originalState.getName());
                newNormalStateMap.put(originalState, newState);
                newFsm.addState(newState);
            }
        }

        // add each transition to fsm

        forEach(from -> {
            final Set<State> newFroms = new HashSet<>();
            if(newNormalStateMap.containsKey(from)){ // from is normal state
                newFroms.add(newNormalStateMap.get(from));
            }else{ // from is split node
                for(final BitField field : outputOfState.get(from)){
                    newFroms.add(newChangedStateMap.get(new StateAndOutput(from, field)));
                }
            }

            from.forEach(transition -> {
                // select goal state
                final State newNextState;
                final var stateAndOutput = new StateAndOutput(transition.getNextState(), transition.getOutput());
                if(newChangedStateMap.containsKey(stateAndOutput)){
                    newNextState = newChangedStateMap.get(stateAndOutput);
                }else {
                    newNextState = newNormalStateMap.get(transition.getNextState());
                }

                // add for each from
                for(final State newFrom : newFroms){
                    newFrom.setTransition(new Transition(transition.getEvent(), newNextState, transition.getOutput()));
                }
            });
        });


        if(initialState != null) {
            // set initial state
            if (newNormalStateMap.containsKey(initialState)) {
                newFsm.initialState = newNormalStateMap.get(initialState);
            } else {
                // there are multiple possible initial states, so introduce new one
                final State newInitialState = new State(initialState.getName() + "__INITIAL");

                // connect to all successors of original initial state
                initialState.forEach(transition -> {
                    final State newNextState;
                    final var stateAndOutput = new StateAndOutput(transition.getNextState(), transition.getOutput());
                    if (newChangedStateMap.containsKey(stateAndOutput)) {
                        newNextState = newChangedStateMap.get(stateAndOutput);
                    } else {
                        newNextState = newNormalStateMap.get(transition.getNextState());
                    }

                    newInitialState.setTransition(new Transition(transition.getEvent(), newNextState, transition.getOutput()));

                });
                newFsm.addState(newInitialState);
                newFsm.initialState = newInitialState;
            }
        }



        return newFsm;
    }
}
