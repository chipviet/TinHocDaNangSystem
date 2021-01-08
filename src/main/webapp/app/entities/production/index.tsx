import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Production from './production';
import ProductionDetail from './production-detail';
import ProductionUpdate from './production-update';
import ProductionDeleteDialog from './production-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Production} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductionDeleteDialog} />
  </>
);

export default Routes;
