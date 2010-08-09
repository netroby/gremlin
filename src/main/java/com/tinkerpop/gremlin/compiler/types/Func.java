package com.tinkerpop.gremlin.compiler.types;

import com.tinkerpop.gremlin.compiler.Tokens;
import com.tinkerpop.gremlin.compiler.context.GremlinScriptContext;
import com.tinkerpop.gremlin.compiler.functions.Function;
import com.tinkerpop.gremlin.compiler.operations.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel A. Yaskevich
 */
public class Func extends DynamicEntity {

    private final Function function;
    private final List<Operation> parameters;
    private final GremlinScriptContext context;
    
    public Func(final Function function, final List<Operation> parameters, final GremlinScriptContext context) {
        this.function = function;
        this.parameters = parameters;
        this.context = context;
    }

    protected Object value() {
        return function.compute(parameters, context).getValue();
    }

    public Function getFunction() {
        return function;
    }

    public List<Operation> getParameters() {
        return parameters;
    }

    public int hashCode() {
        return this.function.hashCode();   
    }

    /*
     * Used to return positions of "." identifiers in function params
     */
    public List<Integer> pipeObjectIndicesInFunctionParams() {
        List<Integer> pipeObjectIndices = new ArrayList<Integer>();

        int position = 0;
        for (Operation parameter : parameters) {
            Atom atom = parameter.compute();

            if (atom.isIdentifier()) {
                String token = atom.getValue().toString();

                if (token.equals(".")) {
                    pipeObjectIndices.add(position);
                }
            }

            position++;
        }

        return pipeObjectIndices;
    }
    
}